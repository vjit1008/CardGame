package com.gurukul.day17.algo;

import java.util.Random;
import java.util.Scanner;

class Card {
	private final String face;
	private final String suit;

	public Card(String face, String suit) {
		this.face = face;
		this.suit = suit;
	}

	public String getFace() {
		return face;
	}

	public String getSuit() {
		return suit;
	}

	@Override
	public String toString() {
		return face + suit;
	}

	public boolean isMatching(Card otherCard) {
		return this.face.equals(otherCard.face) || this.suit.equals(otherCard.suit);
	}
}

public class CardsPlayGame2 {
	public static void main(String[] args) {
		System.out.println("Welcome to Memory!");
		System.out.println(
				"Your goal is to match two cards, using either the same face (i.e. King) or the same suit (i.e. Clubs).");
		System.out.println("If you get an exact match, you get 2 points!");

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("How many points do you want to shoot for? ");
			int goalPoints = sc.nextInt();
			int totalMatches = 0;
			int totalAttempts = 0;
			int score = 0;
			boolean exactMatch = false;

			Card[] cards = new Card[10];
			boolean[] flippedCards = new boolean[10];

			for (int i = 0; i < 10; i++) {
				cards[i] = generateRandomCard();
			}

			while (totalMatches < goalPoints) {
				displayBoard(cards, flippedCards);

				System.out.print("Select first card (or 'q' to quit this round): ");
				int firstIndex = getInput(sc, flippedCards);

				if (firstIndex == -1) {
					break;
				}

				System.out.println("First card is: " + cards[firstIndex]);
				flippedCards[firstIndex] = true;

				System.out.print("Select second card: ");
				int secondIndex = getInput(sc, flippedCards);

				if (secondIndex == -1) {
					break;
				}

				System.out.println("Second card is: " + cards[secondIndex]);
				flippedCards[secondIndex] = true;

				if (cards[firstIndex].isMatching(cards[secondIndex])) {
					totalAttempts++;
					if (firstIndex == secondIndex) {
						exactMatch = true;
						score += 2;
						System.out.println();
					} else {
						score++;
						System.out.println();
					}
					System.out.println(
							"******* Found a match!! " + cards[firstIndex] + " and " + cards[secondIndex] + "******");
					System.out.println("Replacing those with two new cards ");
					System.out.println("Current Score is" + score);
					cards[firstIndex] = generateRandomCard();
					cards[secondIndex] = generateRandomCard();
					flippedCards[firstIndex] = false;
					flippedCards[secondIndex] = false;
				} else {
					System.out.println("Sorry, no match! :( Try again.");
					flippedCards[firstIndex] = false;
					flippedCards[secondIndex] = false;
				}

				totalMatches += score;
			}

			double guessScore = totalAttempts > 0 ? (totalMatches * 100.0) / totalAttempts : 0;
			System.out.println("Congrats, you won!! Your guess score is " + String.format("%.2f", guessScore) + "%");

			if (exactMatch) {
				System.out.println("You got an exact match! You received 2 points for that.");
			}

			System.out.print("Wow, that was fun! You want to play again? (y/n): ");
			String playAgain = sc.next().toLowerCase();
			if (!playAgain.equals("y")) {
				break;
			}
		}

		System.out.println("That was fun, come back if you want more practice! Goodbye!");
	
	}

	private static int getInput(Scanner sc, boolean[] flippedCards) {
		int index;
		while (true) {
			String input = sc.next();
			if (input.equalsIgnoreCase("q")) {
				index = -1;
				break;
			}
			try {
				index = Integer.parseInt(input);
				if (index >= 0 && index < flippedCards.length && !flippedCards[index]) {
					break;
				} else {
					System.out.println("Invalid input. Try again.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		return index;
	}

	private static Card generateRandomCard() {
		String[] suits = { "♠", "♥", "♦", "♣" };
		String[] faces = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };

		Random random = new Random();
		String suit = suits[random.nextInt(4)];
		String face = faces[random.nextInt(13)];

		return new Card(face, suit);
	}

	private static void displayBoard(Card[] cards, boolean[] flippedCards) {
		System.out.print("Current board: ");
		for (int i = 0; i < cards.length; i++) {
			if (flippedCards[i]) {
				System.out.print("| " + cards[i] + " ");
			} else {
				System.out.print("| " + i + " ");
			}
		}
		System.out.println("|");
	}
}
