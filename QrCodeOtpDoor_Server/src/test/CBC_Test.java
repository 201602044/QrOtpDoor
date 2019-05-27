package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import component.BlcokAlgorithm_Service;

class CBC_Test {

	@Test
	void testCBC() {
		BlcokAlgorithm_Service cbc=new BlcokAlgorithm_Service();
		String before="aaaa";
		String After_encrypt=new String(cbc.encrypt(before));
		String After_decrypt=cbc.decrypt(After_encrypt.getBytes());
		assertEquals(before, After_decrypt);
	}
	@Test
	void decodeQrCode() {
		BlcokAlgorithm_Service cbc=new BlcokAlgorithm_Service();
		String After_decrypt=cbc.decrypt("0B/Wq6iSmoVP9BLhVuGfE4tZlYsxajnBukgLSTXYXPE\\u003d".getBytes());
		System.out.println();
	}
}
