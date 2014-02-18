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
  this.j:setDimX(28/32)
  this.j:setDimY(31/32)
end

function collided(this, entity, direction)
  if entity.j:hasTag("guy") then
    this:save()
  end
end

function save(this) 
  if not this.on then
    this.j:save()
    this:turnOn()
  end
end

function turnOn(this)
    this.j:setAnimation("save_point_on")
    this:newTimer(1, function (this) this:turnOff() end, "turn_off")
    this.on = true
end

function turnOff(this) 
    this.j:setAnimation("save_point_off")
    this.on = false
end
return _ENV
end