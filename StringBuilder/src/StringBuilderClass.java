import java.util.Arrays;

public class StringBuilderClass {
	char[] buffer;
	int count;
	private static final int DEFAULT_CAPACITY = 16;

	public StringBuilderClass() {
		this(DEFAULT_CAPACITY);
	}

	public StringBuilderClass(int capacity) {
		buffer = new char[capacity];
	}

	public StringBuilderClass(String str) {
		count = str.length();
		buffer = str.toCharArray();
	}

	public void checkCapacity(int capacity) {
		if (count + capacity < count) {
			return;
		}
		char[] tempArray = new char[count + capacity];
		for (int i = 0; i < count; i++) {
			tempArray[i] = buffer[i];
		}
		buffer = tempArray;
	}

	public void leftShift(int start, int end, int shift) {
		for (int i = start; i < count - shift; i++) {
			buffer[i] = buffer[i + shift];
		}
	}

	public void delete(int start, int end) {
		int shift = end - start;
		leftShift(start, end, shift);
		count -= end - start;
	}

	public void delete(int index) {
		delete(index, index + 1);
		count--;
	}

	public void append(char c) {
		checkCapacity(1);
		buffer[count] = c;
		count++;
	}

	public void append(char[] chArray) {
		int length = chArray.length;
		checkCapacity(length);
		for (int i = 0; i < length; i++) {
			append(chArray[i]);
		}
	}

	public void append(int i) {
		checkCapacity(1);
		buffer[count] = (char) (i + '0');
		count++;
	}

	public void setCharAt(int index, char c) {
		buffer[index] = c;
	}

	public char[] substring(int start, int end) {
		char[] substringBuffer = new char[end - start];
		substringBuffer = Arrays.copyOfRange(buffer, start, end);
		return substringBuffer;
	}

	private void copyChars(char[] str, int offset, int strLength, int pos) {
		for (int i = offset; i < offset + strLength; i++) {
			buffer[i] = str[pos];
			pos++;
		}
	}

	private void rightShift(int start, int end, int shift) {
		for (int i = end; i >= start; i--) {
			buffer[i + shift] = buffer[i];
		}
	}

	public void insert(int offset, char c) {
		checkCapacity(1);
		rightShift(offset, count - 1, 1);
		buffer[offset] = c;
		count++;
	}

	public void insert(int offset, char[] str) {
		insert(offset, str, 0, str.length);
	}

	public void insert(int index, char[] str, int offset, int len) {
		checkCapacity(len);
		rightShift(index, count - 1, len);
		copyChars(str, index, len, offset);
		count += len;
	}

	public void copyString(int start, int end, String str) {
		for (int i = start, pos = 0; i <= end; i++, pos++) {
			buffer[i] = str.charAt(pos);
		}
	}

	public void replace(int start, int end, String str) {
		int strLen = str.length();
		int len = count - (end - start) + strLen;
		
		int diff = strLen - (end - start);
		if (diff > 0) {
			checkCapacity(len);
			rightShift(end - 1, count, diff);
		} else {
			leftShift(start, count, -diff);
		}
		copyString(start, strLen, str);
		
		count = len;
	}

	public int length() {
		return count;
	}

	public String toString() {
		return new String(buffer, 0, count);
	}
}