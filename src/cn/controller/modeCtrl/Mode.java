package cn.controller.modeCtrl;

public class Mode {
	/**
	 * 顺序播放 default
	 * 随机播放 rand 
	 * 单曲循环 onecircle 
	 * 列表循环 morecircle
	 * 单曲播放 one
	 */
	private static String mode="default";
	 
	public static String getMode() {
		return mode;
	}

	public static void setMode(String mode) {
		Mode.mode = mode;
	}
	
}
