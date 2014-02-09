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
  this.j:setAnimation("bullet_normal")
		
  this.j:addTag("projectile")
  
  this.j:addCollision("block");
		
  this.j:setDimX(0.15)
  this.j:setDimY(0.15)
  
  this.collision_selfdamage = 10
end
return _ENV
end