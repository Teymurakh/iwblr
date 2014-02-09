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
  return this
end

function initialize(this)
  		this.j:setAnimation("spike_normal_horizontal")
		this.j:addTag("spike")
  		this.j:addCollision("guy")
		this.j:setHitbox("spike")
		this.j:setRectangular(false)
		this.j:setRotation(0)
		this.j:setRotationVel(135)
		this.j:setDimX(10)
		this.j:setDimY(2)
		this.j:setCollisionDamage(1000)
end


function collided(this, entity, direction)
  if not (this.j:getTeam() == entity.j:getTeam()) then
    if not entity.j:isInvulnerable() then
      entity.j:takeDamage(this.j:getCollisionDamage())
    end
  end
end
return _ENV
end