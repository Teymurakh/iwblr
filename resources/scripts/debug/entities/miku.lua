--
do
local newenv = {}
setAllowed(newenv)
take_from_parent(lib.loadentity("entity"), newenv)
local _ENV = newenv
_ENV.__index = _ENV

function new(javaObj)
  local this = setmetatable({}, _ENV)
  this.j = javaObj
  this.collision_damage = 0
  this.collision_selfdamage = 0
  this.max_health = 1
  this.health = 1
  this.is_invulnerable = false
  this.team = "neutral"
  this.timers = {}
  return this
end

function initialize(this)
  this.j:setAnimation("block_dirt_1")
  this.j:addCollision("guy")
  this.j:addCollision("block")
  this.j:setDimX(7)
  this.j:setDimY(14)
  this.j:setHitbox("rectangle")
  this.j:setRectangular(false)
  
  this.timers.shoot_timer = this:newTimer2(100, function (this) this:shoot() end)
  this.timers.fly_timer = this:newTimer2(2000, function (this) this:flyaway() end)
end

function shoot(this)
  velX, velY = lib.angle(10, math.random(0, 360))	
  local newLuaEntity = newClass("projectile");
	
  newLuaEntity:addCollision("guy")
  newLuaEntity:setVelX(velX)
  newLuaEntity:setVelY(velY)
		
  newLuaEntity:setDimX(0.5)
  newLuaEntity:setDimY(0.5)
  newLuaEntity:setRotationVel(0)
		
  local posX = this.j:getCenterX() - newLuaEntity:getDimX()/2
  local posY = this.j:getCenterY() + newLuaEntity:getDimY()/2
	
  this.j:createEntity(newLuaEntity, posX, posY)
end

function flyaway(this)
  this.j:setRotationVel(270)
  this.j:setVelX(-20)
end
return _ENV
end