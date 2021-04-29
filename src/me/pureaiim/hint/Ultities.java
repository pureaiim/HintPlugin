package me.pureaiim.hint;

public class Ultities {

	private Main plugin;

	public Ultities(Main pl) {
		this.plugin = pl;
	}

	public String getHint() {
		return plugin.getConfig().getString("hint");
	}

	public String gHVH() {
		String msg = "";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < plugin.getConfig().getString("hint").length(); i++) {
			if (plugin.getConfig().getString("hint").substring(i, i + 1).equals("_")) {
				builder.append(msg + "-");
			} else {
				builder.append(msg + "_");
			}
		}
		msg = builder.toString();

		return msg;
	}
}
