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
  this.j:setAnimation("block_pipe")
  this.j:addTag("block")
  this.j:addTag("physical")
  this.j:addCollision("guy")
end

function die(this)
  for i = 0, 6 do
  
    local angle = 0
    local scatter = math.random() * 360
    local usedAngle = angle + scatter
	
    local posX = this.j:getPosX() + math.random()*this.j:getDimX()
    local posY = this.j:getPosY() - math.random()*this.j:getDimY()
	
    local sizeMultiplier = 0.25 + math.random() * 0.25;
	
    local dimX = this.j:getDimX()*sizeMultiplier
    local dimY = this.j:getDimY()*sizeMultiplier
			
    local velX = math.random()*6-3
    local velY = math.random()*6-3
	
	local rotation = 360 - math.random()*720
	local rotation_vel = 360 - math.random()*720
  
    local new_e = lib.newEntity("particle")
    new_e.j:setAnimation(this.j:getAnimation())
  
    this:shoot(new_e, posX, posY, velX, velY, dimX, dimY, rotation, 1)
  
  end
  
  this.j:die()
end
return _ENV
end