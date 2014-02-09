

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
        this.j:setDimY(4)
end

function collided(this, entity, direction)
  if entity:hasTypeTag("guy") then 
    if not this.activated then
      this.activated = true
      for i = 0,4 do
        local new_spike = newClass("spike0")
      
        local x = this.j:getPosX() -5
        local y = this.j:getPosY() -i
      
        new_spike:setVelX(7)
      
  
        this.j:createEntity(new_spike, x, y)
      end
      this.j:destroy()
    end 
  end
end
return _ENV
end