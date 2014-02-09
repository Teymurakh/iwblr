--
do
local newenv = {}
setAllowed(newenv)
take_from_parent(lib.loadentity("spike0"), newenv)
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
  super_initialize(this)
  this.j:setRotation(180)
end
return _ENV
end