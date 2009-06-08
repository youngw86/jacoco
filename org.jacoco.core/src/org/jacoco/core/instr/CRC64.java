package org.jacoco.core.instr;

/**
 * CRC64 checksum calculator based on the polynom specified in ISO 3309. The
 * implementation is based on the following publications:
 * 
 * <ul>
 * <li>http://en.wikipedia.org/wiki/Cyclic_redundancy_check</li>
 * <li>http://www.geocities.com/SiliconValley/Pines/8659/crc.htm</li>
 * </ul>
 * 
 * @author Marc R. Hoffmann
 * @version $Revision: $
 */
public final class CRC64 {

	private static final long POLY64REV = 0xd800000000000000L;

	private static final long[] LOOKUPTABLE;

	static {
		LOOKUPTABLE = new long[0x100];
		for (int i = 0; i < 0x100; i++) {
			long v = i;
			for (int j = 0; j < 8; j++) {
				if ((v & 1) == 1) {
					v = (v >>> 1) ^ POLY64REV;
				} else {
					v = (v >>> 1);
				}
			}
			LOOKUPTABLE[i] = v;
		}
	}

	/**
	 * Calculates the CRC64 checksum for the given data array.
	 * 
	 * @param data
	 *            data to calculate checksum for
	 * @return checksum value
	 */
	public static long checksum(byte[] data) {
		long sum = 0;
		for (int i = 0; i < data.length; i++) {
			final int lookupidx = ((int) sum ^ data[i]) & 0xff;
			sum = (sum >>> 8) ^ LOOKUPTABLE[lookupidx];
		}
		return sum;
	}

	private CRC64() {
	}

}