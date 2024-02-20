package draft.dev;

//Assembly AI Imports
import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Main {

    public static void main(String[]args) {
        AssemblyAI client = AssemblyAI.builder().apiKey("ec855817c8824b0d8019eb4e6f80cb93").build(); //Remember to remove API Key

        try {
            File audioFile = new File("./audio/apple_public_earnings_call.mp3");
            //Create a new output file and filewriter for the transcription
            File outputFile = new File("output.txt");
            outputFile.createNewFile();
            FileWriter fileWriter = new FileWriter(outputFile);
            //Transcribe audio file
            Transcript transcript = client.transcripts().transcribe(audioFile);
            //Checks to see if transcription was successful
            if (transcript.getStatus().toString().contentEquals("error")) {
                System.err.println(transcript.getError());
            } else {
                //Write transcript to output file
                fileWriter.write(String.valueOf(transcript.getText()));
                fileWriter.close();
                //Opens output file
                Desktop desktop = Desktop.getDesktop();
                desktop.open(outputFile);
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
