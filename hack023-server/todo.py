""" Basic todo list using webpy 0.3 """
import web
import model
import json

### Url mappings

urls = (
    '/', 'Index',
    '/login', 'Login',
    '/logout', 'Logout',
    '/del/(\d+)', 'Delete',
    '/service/login/(.*)/(.*)', 'ServiceLogin',
    '/service/todo/', 'ServiceTodoGet',
    '/service/todo/add/(.*)', 'ServiceTodoAdd',
    '/service/todo/del/(\d+)', 'ServiceTodoDelete',
)


web.config.debug = False
render = web.template.render('templates', base='base')
app = web.application(urls, locals())
session = web.session.Session(app, web.session.DiskStore('sessions'))

allowed = (
    ('user','pass'),
)

class Login:

    login_form = web.form.Form( web.form.Textbox('username', web.form.notnull),
        web.form.Password('password', web.form.notnull),
        web.form.Button('Login'),
        )

    def GET(self):
        f = self.login_form()
        return render.login(f)

    def POST(self):
        if not self.login_form.validates():
            return render.login(self.login_form)

        username = self.login_form['username'].value
        password = self.login_form['password'].value
        if (username,password) in allowed:
            session.logged_in = True
            raise web.seeother('/')

        return render.login(self.login_form)


class Logout:
    def GET(self):
        session.logged_in = False
        raise web.seeother('/')

class Index:

    form = web.form.Form(
        web.form.Textbox('title', web.form.notnull, 
            description="I need to:"),
        web.form.Button('Add todo'),
    )

    def GET(self):
        if session.get('logged_in', False):
            """ Show page """
            todos = model.get_todos()
            form = self.form()
            return render.index(todos, form)
        else:
            raise web.seeother('/login')

    def POST(self):
        """ Add new entry """
        form = self.form()
        if not form.validates():
            todos = model.get_todos()
            return render.index(todos, form)
        model.new_todo(form.d.title)
        raise web.seeother('/')


class Delete:

    def POST(self, id):
        """ Delete based on ID """
        id = int(id)
        model.del_todo(id)
        raise web.seeother('/')

class ServiceLogin:
    def GET(self, username, password):
        if (username,password) in allowed:
            session.logged_in = True
            web.header('Content-Type', 'application/json')
            result = {'result':'ok'}
            return json.dumps(result)
        else:
            web.header('Content-Type', 'application/json')
            result = {'result':'error'}
            return json.dumps(result)

class ServiceTodoGet:
    def GET(self):
        if not session.get('logged_in', False):
            raise web.HTTPError("401 unauthorized", {}, "unauthorized")

        web.header('Content-Type', 'application/json')
        todos = model.get_todos()
        return json.dumps(todos.list())

class ServiceTodoAdd:
    def GET(self, title):
        if not session.get('logged_in', False):
            raise web.HTTPError("401 unauthorized", {}, "unauthorized")

        result = model.new_todo(title)
        web.header('Content-Type', 'application/json')
        return json.dumps(result.list())

class ServiceTodoDelete:
    def GET(self, id):
        if not session.get('logged_in', False):
            raise web.HTTPError("401 unauthorized", {}, "unauthorized")

        id = int(id)
        model.del_todo(id)
        web.header('Content-Type', 'application/json')
        result = {'result':'ok'}
        return json.dumps(result)


app = web.application(urls, globals())

if web.config.get('_session') is None:
    session = web.session.Session(app, web.session.DiskStore('sessions'), {'count': 0})
    web.config._session = session
else:
    session = web.config._session

if __name__ == '__main__':
    app.run()

