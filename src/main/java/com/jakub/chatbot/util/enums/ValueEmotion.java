package com.jakub.chatbot.util.enums;

public enum ValueEmotion {

	STRONGPOSITIVE {
		@Override
		public int getValue() {
			return 2;
		}
	},
	WEAKPOSITIVE {
		@Override
		public int getValue() {
			return 1;
		}
	},
	NATURAL {
		@Override
		public int getValue() {
			return 0;
		}
	},
	WEAKNEGATIVE {
		@Override
		public int getValue() {
			return -1;
		}
	},
	STRONGNEGATIVE {
		@Override
		public int getValue() {
			return -2;
		}
	};

	public abstract int getValue();

}
