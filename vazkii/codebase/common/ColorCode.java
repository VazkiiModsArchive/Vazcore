package vazkii.codebase.common;

public enum ColorCode {

	BLACK('0'), DARK_BLUE('1'), DARK_GREEN('2'), DARK_AQUA('3'), DARK_RED('4'), PURPLE('5'), GOLD('6'), GREY('7'), DARK_GREY('8'), INDIGO('9'), BRIGHT_GREEN('a'), AQUA('b'), RED('c'), PINK('d'), YELLOW('e'), WHITE('f');

	private ColorCode(char color) {
		this.color = color;
	}

	char color;

	@Override
	public String toString() {
		return new StringBuilder().append(VazcoreReference.FORMATTING_CODE_CHAR).append(color).toString();
	}
}
