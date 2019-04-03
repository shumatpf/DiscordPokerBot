package Blackjack;

import java.util.ArrayList;

import Deck.Deck;

import net.dv8tion.jda.core.entities.User;

public class Game {

	ArrayList<Player> players;
	Deck deck;
	Player creator;

	// CONSTRUCTOR
	public Game(User user) {
		players = new ArrayList<>();
		deck = new Deck();
		creator = new Player(user);
		for (int i = 0; i < 10; i++) {
			deck.shuffle();
		}
	}

	// get the creator of the game
	public Player getCreator() {
		return creator;
	}

	// get the list of players
	public ArrayList<Player> getPlayers() {
		return players;
	}

	// return the player object associated with user
	public Player getPlayer(User user) {
		for (Player player : players) {
			if (player.user.getName().equals(user.getName())) {
				return player;
			}
		}
		return null;
	}

	// add a user
	public boolean add(User user) {
		for (Player p : players) {
			if (user.getName().equals(p.user.getName()))
				return false;
		}
		players.add(new Player(user));
		return true;
	}

	// remove a user
	public boolean remove(User user) {
		Player temp = this.getPlayer(user);
		return players.remove(temp);
	}

	// deals num cards to each player - used at start of game
	public boolean deal(int num) {

		if (players.size() * num >= 52) {
			return false;
		}

		for (Player player : players) {
			dealHand(player.user, num);
		}

		return true;
	}

	// deals num cards to one person - used at start and whenever a player hits
	public void dealHand(User user, int num) {
		Player p = this.getPlayer(user);
		for (int i = 0; i < num; i++) {
			p.addCard(deck.draw());
		}
	}

	// displays the cards visible to all players in the common channel
	public String displayVisCards() {
		String str = "";
		for (Player player : players) {
			str += player.showVisCards();
		}
		return str;
	}

	// returns true if the player is in the game
	public boolean containsPlayer(User user) {
		for (Player p : players) {
			if (p.user.getName().equals(user.getName())) {
				return true;
			}
		}
		return false;
	}

	public boolean allStanding() {
		for (Player player : players) {
			if (!player.isStand()) {
				return false;
			}
		}
		return true;
	}

	public boolean isCreator(User user) {
		return user.getName().equals(creator.user.getName());
	}

	// resets the game - creates new deck and removes cards from all players
	public void reset() {
		for (Player player : players) {
			player.removeAll();
		}
		deck = new Deck();
	}

	// ends the game - dont know if this has much functionality
	public void end() {
		deck = new Deck();
		players = new ArrayList<>();
	}

}