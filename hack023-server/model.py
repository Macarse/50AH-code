import web

db = web.database(dbn='sqlite', db='todo.db')


def get_todos():
    return db.select('todo', order='id')

def new_todo(text):
    id = db.insert('todo', title=text)
    return db.select('todo', where="id=$id", vars=locals())

def del_todo(id):
    db.delete('todo', where="id=$id", vars=locals())

