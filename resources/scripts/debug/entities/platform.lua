
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
  --entity:initialize()
  this.j:setAnimation("platform_wood")
  this.j:addTag("platform")
  this.j:addTag("physical")
  this.j:addCollision("guy")
  this.j:setRectangular(true)
  this.j:setPlatform(true)
  this.j:setDimX(1)
  this.j:setDimY(0.5)
  
  this.is_platform = true
end
return _ENV
end