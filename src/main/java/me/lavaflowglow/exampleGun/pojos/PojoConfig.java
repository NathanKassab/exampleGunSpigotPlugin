package me.lavaflowglow.exampleGun.pojos;

public class PojoConfig {

	private String gunName = "AK-47";
	private String[] gunLore = new String[] { "A gun that shoots stuff", "big gun" };
	private int maxAmmo = 30;
	private long reloadTimeInMillis = 2000, fireDelayInMillis = 100;

	public String getGunName() {
		return gunName;
	}

	public void setGunName(String gunName) {
		this.gunName = gunName;
	}

	public String[] getGunLore() {
		return gunLore;
	}

	public void setGunLore(String[] gunLore) {
		this.gunLore = gunLore;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}

	public long getReloadTimeInMillis() {
		return reloadTimeInMillis;
	}

	public void setReloadTimeInMillis(long reloadTimeInMillis) {
		this.reloadTimeInMillis = reloadTimeInMillis;
	}

	public long getFireDelayInMillis() {
		return fireDelayInMillis;
	}

	public void setFireDelayInMillis(long fireDelayInMillis) {
		this.fireDelayInMillis = fireDelayInMillis;
	}

}
