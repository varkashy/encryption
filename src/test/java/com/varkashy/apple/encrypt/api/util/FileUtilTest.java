package com.varkashy.apple.encrypt.api.util;

import org.junit.AfterClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class FileUtilTest {

    private static final String TEST_FILE_PATH = "src/test/resources/testFile";
    private static final String DUMMY_FILE_PATH = "src/test/no-path/dummyFile";

    @org.junit.jupiter.api.Test
    void testReadWriteFeature() throws IOException {
        FileUtil.writeToFile("test".getBytes(),TEST_FILE_PATH);
        assertEquals("test",new String(FileUtil.readFromFile(TEST_FILE_PATH)));
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void testFileNotExistReadFeature() {
        assert(FileUtil.readFromFile(DUMMY_FILE_PATH).length==0);
    }

    @org.junit.jupiter.api.Test
    void testFileNotExistWriteFeature() {
        try{
            FileUtil.writeToFile("test".getBytes(),DUMMY_FILE_PATH);
            fail();
        } catch (IOException e) {
            // Do Nothing
        }
    }

}