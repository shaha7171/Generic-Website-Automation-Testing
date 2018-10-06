package com.snpm.util;

public class Retry {
	public static final int DEFAULT_RETRIES = 3;
	public static final long DEFAULT_WAIT_TIME_IN_MILLI = 60000;

	private int numberOfRetries;
	private int numberOfTriesLeft;
	private long timeToWait;

	public Retry() {
		this(DEFAULT_RETRIES, DEFAULT_WAIT_TIME_IN_MILLI);
	}

	public Retry(int numberOfRetries,
			long timeToWait) {
		this.numberOfRetries = numberOfRetries;
		numberOfTriesLeft = numberOfRetries;
		this.timeToWait = timeToWait;
	}

	/**
	 * @return true if there are tries left
	 */
	public boolean shouldRetry() {
		return numberOfTriesLeft > 0;
	}

	public void errorOccured() throws Exception {
		numberOfTriesLeft--;
		if (!shouldRetry()) {
			System.out.println("Retry exceeded the maximum number of try configured");
			throw new Exception("Retry Failed: Total " + numberOfRetries
					+ " attempts made at interval " + getTimeToWait()
					+ "ms");
		}
		waitUntilNextTry();
	}

	public long getTimeToWait() {
		return timeToWait;
	}

	private void waitUntilNextTry() {
		try {
			Thread.sleep(getTimeToWait());
		} catch (InterruptedException ignored) {
		}
	}
}
