--#extends entity

function initialize(this)		
  this.j:setAnimation("bullet_normal")
		
  this.j:addTag("projectile")
  
  this.j:addCollision("block");
		
  this.j:setDimX(0.15)
  this.j:setDimY(0.15)
  
  this.collision_selfdamage = 10
end