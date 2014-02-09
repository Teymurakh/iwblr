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
  this.j:setAnimation("block_brick_1")
  this.j:addCollision("guy")
  this.j:setDimX(0.25)
  this.j:setDimY(0.25)
  
  this.shoot_timer = this.j:createTimer("shoot", 500, 5)
end

function shoot(this)
			
		velX, velY = lib.angle(10, math.random(0, 360))
			
		local newLuaEntity = newClass("projectile");
	
	    newLuaEntity:addCollision("guy")
		newLuaEntity:setVelX(velX)
		newLuaEntity:setVelY(velY)
		
		newLuaEntity:setDimX(0.5)
		newLuaEntity:setDimY(0.5)
		newLuaEntity:setRotationVel(270)
		
		
	
		local posX = this.j:getCenterX() - newLuaEntity:getDimX()/2
		local posY = this.j:getCenterY() + newLuaEntity:getDimY()/2
	
		this.j:createEntity(newLuaEntity, posX, posY)
end


return _ENV
end