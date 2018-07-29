import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileManagerTest {

    @Test
    public void moviePathsTest() {
        ArrayList<File> list = new ArrayList<>();
        File firstFile = new File("test.avi");
        File secondFile = new File("test2.txt");
        File thirdFile = new File("test3.mkv");

        try {
            firstFile.createNewFile();
            secondFile.createNewFile();
            thirdFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(true, firstFile.exists());
        assertEquals(true, secondFile.exists());
        assertEquals(true, thirdFile.exists());

        list.add(firstFile);
        list.add(secondFile);
        list.add(thirdFile);

        FileManager fileManager = new FileManager();

        assertEquals(2, fileManager.getMoviePaths(list).size());

        firstFile.delete();
        secondFile.delete();
        thirdFile.delete();

        assertEquals(true, !firstFile.exists());
        assertEquals(true, !secondFile.exists());
        assertEquals(true, !thirdFile.exists());
    }

    @Test
    public void createConfigFileTest() {
        File file = new File("config" + FileManager.separator + "test.txt");

        FileManager.createConfigFile(file);

        assertEquals(true, file.exists());

        file.delete();

        assertEquals(true, !file.exists());
    }

}
