package cn.controller.modeCtrl;

public class Mode {
	/**
	 * ˳�򲥷� default
	 * ������� rand 
	 * ����ѭ�� onecircle 
	 * �б�ѭ�� morecircle
	 * �������� one
	 */
	private static String mode="default";
	 
	public static String getMode() {
		return mode;
	}

	public static void setMode(String mode) {
		Mode.mode = mode;
	}
	
}
