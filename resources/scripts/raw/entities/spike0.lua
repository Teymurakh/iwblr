--#extends entity

function initialize(this)
  		this.j:setAnimation("spike_normal")
  		this.j:addCollision("guy")
		this.j:addTag("spike")
		this.j:setHitbox("spike")
		this.j:setRectangular(false)
		this.j:setRotation(0)
		this.j:setDimX(1)
		this.j:setDimY(1)
		
		this.collision_damage = 1000
		this.team = "spike"
end


