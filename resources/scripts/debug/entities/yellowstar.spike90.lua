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
  		this.j:setAnimation("spike_yellow")
		this.j:addTag("spike")
  		this.j:addCollision("guy")
		this.j:setHitbox("spike")
		this.j:setRectangular(false)
		this.j:setRotation(0)
		this.j:setDimX(1)
		this.j:setDimY(1)
 		this.j:setRotation(90)
		
		this.collision_damage = 1000
		this.team = "spike"
end
return _ENV
end