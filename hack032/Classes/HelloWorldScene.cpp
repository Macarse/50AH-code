#include "HelloWorldScene.h"
#include "SimpleAudioEngine.h"

using namespace cocos2d;
using namespace CocosDenshion;

CCScene* HelloWorld::scene()
{
    // 'scene' is an autorelease object
    CCScene *scene = CCScene::create();
    
    // 'layer' is an autorelease object
    HelloWorld *layer = HelloWorld::create();

    // add layer as a child to scene
    scene->addChild(layer);

    // return the scene
    return scene;
}

// on "init" you need to initialize your instance
bool HelloWorld::init()
{
    //////////////////////////////
    // 1. super init first
    if ( !CCLayer::init() )
    {
        return false;
    }

m_emitter = CCParticleSnow::node();
     m_emitter->retain();
    this->addChild(m_emitter, 10);
    
    m_emitter->setLife(4);
    m_emitter->setLifeVar(1);
    
    // gravity
    m_emitter->setGravity(CCPointMake(0,-10));
        
    // speed of particles
    m_emitter->setSpeed(130);
    m_emitter->setSpeedVar(30);

    ccColor4F startColor = m_emitter->getStartColor();
    startColor.r = 0.9f;
    startColor.g = 0.9f;
    startColor.b = 0.9f;
    m_emitter->setStartColor(startColor);
    
    ccColor4F startColorVar = m_emitter->getStartColorVar();
    startColorVar.b = 0.1f;
    m_emitter->setStartColorVar(startColorVar);
    
    m_emitter->setEmissionRate(m_emitter->getTotalParticles()/m_emitter->getLife());
    m_emitter->setTexture( CCTextureCache::sharedTextureCache()->addImage("snow.png") );

     CCSize s = CCDirector::sharedDirector()->getWinSize();
    m_emitter->setPosition( CCPointMake(s.width / 2, s.height) );
    
    return true;
}

void HelloWorld::menuCloseCallback(CCObject* pSender)
{
    CCDirector::sharedDirector()->end();

#if (CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    exit(0);
#endif
}
