--
do
local newenv = {}
setAllowed(newenv)
take_from_parent(lib.loadentity("platform"), newenv)
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
  return this
end

function initialize(this)
  super_initialize(this)
  this.j:addCollision("block")
end

function collided(this, entity, direction)
  if entity.j:hasTypeTag("block") then
  
    if direction == 0 or direction == 180 then 
      this.j:setVelX(-this.j:getVelX())
    end
    
    if direction == 90 or direction == 270 then 
      this.j:setVelY(-this.j:getVelY())
    end
    
  end
end
return _ENV
end