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
        this.j:setAnimation("save_point_off")
        this.j:addCollision("guy")
        this.j:setDimX(0.75)
        this.j:setDimY(0.75)
end

function collided(this, entity, direction)
  if entity.j:hasTag("guy") then
    this.j:save()
  end
end
return _ENV
end