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
 		this.activated = false
        this.j:setAnimation("invisible")
        this.j:addCollision("guy")
        this.j:setDimX(1)
        this.j:setDimY(0.25)
end

function collided(this, entity, direction)
  if entity.j:hasTag("guy") then 
    if not this.activated then
      this.activated = true
      this:trigger_action()
      this:die()
    end
  end
end

function trigger_action(this)
  local new_e = lib.newEntity("block_connector");
  local pos_x = this.j:getPosX()	
  local pos_y = this.j:getPosY() + 1.75
		
  this.j:createEntity(new_e.j, pos_x, pos_y)
end
return _ENV
end