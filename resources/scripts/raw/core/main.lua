
do

-----------------------------------------------
-------------------Java interface
-----------------------------------------------
function initialize(toLoad)
  rootpath = "resources/scripts/compiled"
  postfix = ""

  allowed = {}
  allowed.print = print
  allowed.setmetatable = setmetatable
  allowed.tostring = tostring
  allowed.newClass = newClass
  allowed.math = math
  allowed.ipairs = ipairs
  allowed.pairs = pairs
  allowed.take_from_parent = take_from_parent
  allowed.lib = lib
  allowed.table = table

  classes = {}
  entities = {}

  local entityTable = arrayToTable(toLoad)
  for key,value in pairs(entityTable) do
    classes[value] = dofile(rootpath .. "/entities/" .. value .. postfix)
    print(rootpath .. "/entities/" .. value .. " loaded" .. postfix) 
  end
end

function create(e, id)
  local name = e:getScriptName()
  local newEntity = classes[name].new(e)
  newEntity:initialize()
  entities[id] = newEntity
end


function update(id, delta)
  local entity = entities[id]
  entity:update(delta)
end

function death(id)
  entities[id]:death()
end

function placed(id)
  entities[id]:placed()
end

function collided(id, entity, direction)
  entities[id]:collided(entities[entity], direction)
end


function call(id, f_name, arg)
  local entity = entities[id]
  entity[f_name](entity, arg)
end


function invoke(...)
  local values = {}
  for k,v in pairs{...} do
    values[k] = v
  end
  
  local entity = entities[values[1]]
  entity[values[2]](entity, values[3], values[4], values[5], values[6], values[7])
  
     
end

-----------------------------------------------
-----------------------------------------------
-----------------------------------------------
-----------------------------------------------

lib = {}

function lib.angle(speed, angle) 
		local x = speed * math.cos(math.rad(angle))
		local y = speed * math.sin(math.rad(angle))
		
		return x, y
end


function lib.angleBetween(x1, y1, x2, y2) 


		local xToYRatio = math.abs(y1-y2) / math.abs(x1-x2)
		local angle = math.deg(math.atan(xToYRatio));

		local directionX = x2 - x1;
		local directionY = y2 - y1;
		local geographicAngle = 0;
		
		if directionX > 0 and directionY > 0 then
			geographicAngle = angle
		end
		
		if directionX < 0 and directionY > 0 then
			geographicAngle = 180 - angle
		end
		
		if directionX < 0 and directionY < 0 then
			geographicAngle = angle + 180
		end
		
		if directionX > 0 and directionY < 0 then
			geographicAngle = 360 - angle
		end
		
		return geographicAngle;

end



function lib.center(entity1, entity2) 
		local x = speed * math.cos(math.rad(angle))
		local y = speed * math.sin(math.rad(angle))
		
		return x, y
end


function lib.loadentity(name) 
  return dofile(rootpath .. "/entities/" .. name .. postfix)
end

function lib.newEntity(name) 
  local newJEntity = luajava.newInstance("com.teymurakh.iwblr.entities.Entity", name);
  local newId = newJEntity:getIntId()
  return entities[newId]
end

function lib.floatEqual(a, b, precision)
  return (math.abs(a - b) < precision)
end



function arrayToTable(array) 
local newTable = {}
for i = 0, array:size()-1 do
  newTable[i] = array:get(i)
end

return newTable
end



function newClass(name) 
  return luajava.newInstance("com.teymurakh.iwblr.entities.Entity", name);
end

function setAllowed(t)
  for orig_key, orig_value in pairs(allowed) do
    t[orig_key] = orig_value
  end
end

function take_from_parent(t2, t)
  for orig_key, orig_value in pairs(t2) do
    t[orig_key] = orig_value
    t["super_" .. orig_key] = orig_value
  end
end

end
