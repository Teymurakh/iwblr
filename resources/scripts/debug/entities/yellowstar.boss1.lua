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
  this.j:setAnimation("block_dirt_1")
  this.j:addCollision("guy")
  this.j:setDimX(4)
  this.j:setDimY(6)
  this.j:setHitbox("rectangle")
  this.j:setRectangular(false)
  this:createActionTimers()
end

function createActionTimers(this)
  
  local time = 1.800
  this:newTimer(time, function (this) this:move(-7, 0, 0.150) end, "move_in")

  
  time = time + 0.400
  this:newTimer(time, function (this) this:attack1() end, "attack1")
  
  
  time = time + 4.000
  this:newTimer(time, function (this) this:move(-16, 0, 1.0) end, "move_left")
  
  
  time = time + 1.0
  this:newTimer(time, function (this) this:attack2() end, "attack2")
  
  time = time + 3.000
  this:newTimer(time, function (this) this:move(16, 0, 1.000) end, "move_right")
  
  
  time = time + 3.000
  this:newTimer(time, function (this) this:attack3() end, "attack3")
  
end

function stop(this) 
  this.j:setVelX(0)
  this.j:setVelY(0)
end


function move(this, x, y, time)
  local vel_x = x / time
  local vel_y = y / time
  
  this.j:setVelX(vel_x)
  this.j:setVelY(vel_y)
  
  this:newTimer(time, function (this) this:stop() end, "stop")
end



function shoot_special(this, speed, angle, size, life_time)
  local velX, velY = lib.angle(speed, angle)
  
  local new_e = lib.newEntity("projectile")
  new_e.collision_damage = 10
  
  
  local posX = this.j:getCenterX() - new_e.j:getDimX()/2
  local posY = this.j:getCenterY() + new_e.j:getDimY()/2
  
  this:shoot(new_e, posX, posY, velX, velY, size, size, angle, 5)
end





------------------------------------------------------------------------
---------------------------ATTACK 1
------------------------------------------------------------------------
function attack1(this) 
  this.attack_shoot1_count = 0
  for i = 0, 10 do
    this:newTimer(i * 0.200, function (this) this:attack1_shoot(i) end)
  end
end

function attack1_shoot(this, count)
  for i2 = 0,36 do
    this:shoot_special(10, (i2  * 10) - count * 6, 0.5, 3000)
  end
end


------------------------------------------------------------------------
---------------------------ATTACK 2
------------------------------------------------------------------------
function attack2(this)
  for i = 0, 25 do
    this:newTimer(i * 0.100, function (this) this:attack2_shoot1(i) end)
  end
  
  for i = 0, 25 do
    this:newTimer(i * 0.100 + 2.500, function (this) this:attack2_shoot2(i) end)
  end
end

function attack2_shoot1(this, count)
  for i2 = 0,5 do
    this:shoot_special(10, (i2  * 60) + count * 4, 1, 3000)
  end
end

function attack2_shoot2(this, count)
  for i2 = 0,5 do
    this:shoot_special(10, (i2  * 60) - count * 4 + 100, 1, 3000)
  end
end

------------------------------------------------------------------------
---------------------------ATTACK 3
------------------------------------------------------------------------

function attack3(this) 
  this:attack3_shoot1()
  
  
  for i = 0, 300 do
    this:newTimer(i * 0.150, function (this) this:attack3_shoot2(i) end)
  end
  
end


function attack3_shoot1(this)
  local start_pos_x = 1
  local start_pos_y = 1

  for i2 = 0,22 do
    this:newTimer(i2 * 0.150, function (this) this:shoot_new("spike0", start_pos_x + i2, start_pos_y, 0, 0, 1, 1, 90, 10000) end)
  end
  
  for i2 = 0,15 do
    this:newTimer(i2 * 0.150, function (this) this:shoot_new("spike0", start_pos_x, start_pos_y + i2 + 1, 0, 0, 1, 1, 0, 10000) end)
  end
  
end

function attack3_shoot2(this, i)
  this:shoot_new("block", this.j:getPosX(), this.j:getPosY() - math.random() * 15, -math.random(2, 6), 0, 1, 0.5, 0, 10)
  if math.fmod(i, 3) == 0 then
    this:shoot_new("spike0", this.j:getPosX(), this.j:getPosY() - math.random() * 15, -math.random(2, 6), 0, 1, 1, 180, 10)
  end
end

function shoot_new(this, name, pos_x, pos_y, vel_x, vel_y, size_x, size_y, angle, life_time)
  local new_e = lib.newEntity(name);
  
  new_e.j:addCollision("guy")
  new_e.j:setVelX(vel_x)
  new_e.j:setVelY(vel_y)
		
  new_e.j:setDimX(size_x)
  new_e.j:setDimY(size_y)
  
  new_e.j:setRotation(angle)
  
  new_e:newTimer(life_time, function (this) this:die() end, "die")
		
		
  this.j:createEntity(new_e.j, pos_x, pos_y)
end

function place_spike(this, pos_x, pos_y, angle, life_time)
  local new_e = lib.newEntity("spike0");
	
  new_e.j:addCollision("guy")
  new_e.j:setRotation(angle)
		
  new_e.j:setDimX(1)
  new_e.j:setDimY(1)
  
  new_e:newTimer(life_time, function (this) this:die() end, "die")
		
  this.j:createEntity(new_e.j, pos_x, pos_y)
end
return _ENV
end