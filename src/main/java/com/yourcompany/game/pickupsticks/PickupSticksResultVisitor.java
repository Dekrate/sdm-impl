package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.pickupsticks.PickupSticksResult; // Import PickupSticksResult
import com.yourcompany.game.pickupsticks.PickupSticksPlayer; // Import PickupSticksPlayer

public interface PickupSticksResultVisitor {

	
	void visit(PickupSticksResult result);



}
