package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.pickupsticks.PickupSticksResult;
import com.yourcompany.game.pickupsticks.PickupSticksPlayer;

public interface PickupSticksResultVisitor {

	
	void visit(PickupSticksResult result);



}
