
#extends entity

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
