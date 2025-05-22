package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.MatchCommand;
import com.yourcompany.game.pickupsticks.PickupSticksMatch;
import com.yourcompany.game.pickupsticks.PickupSticksPlayer;
import com.yourcompany.game.Match;
import com.yourcompany.game.Contestant;
import com.yourcompany.game.Result;

public class CollectStickCommand implements MatchCommand<PickupSticksMatch> {

  private PickupSticksPlayer player;
  private StickType stickType;

  
  public CollectStickCommand(PickupSticksPlayer player, StickType stickType) {
    this.player = player;
    this.stickType = stickType;
  }

  
  @Override
  public void execute(PickupSticksMatch match) {
    if (match != null && player != null && stickType != null) {
      match.recordStickCollection(player, stickType);
    } else {
      System.err.println("Error executing CollectStickCommand: Match, player, or stick type is null.");
    }
  }
}
