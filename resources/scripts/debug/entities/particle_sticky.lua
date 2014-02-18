
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
  this.j:addCollision("block")
  this.j:addCollision("spike")
  this.j:setAffectedByGravity(true)
end

function collided(this, entity, direction)
  if not (this.team == entity.team) then
    this.j:setVelX(0)
    this.j:setVelY(0)
    
    
  end
end
return _ENV
end