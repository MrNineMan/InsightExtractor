package draft.dev;
// Begin Assembly AI Imports

import com.assemblyai.api.AssemblyAI;

import com.assemblyai.api.resources.transcripts.types.Transcript;

// End Assembly AI Imports

import java.awt.Desktop;

import java.io.File;

import java.io.IOException;

import java.io.FileWriter;

public class Main {

	public static void main(String[]args) { 
	
		AssemblyAI client = AssemblyAI.builder().apiKey("62053e25eb0b4512a79e1dae34744f83").build();
		try {
			// Apple public earnings call audio file   
			File audioFile = new File("./audio/apple_public_earnings_call.mp3");

			// Create a new output file and filewriter for the transcription

			File outputFile = new File("output.txt");

			outputFile.createNewFile();

			FileWriter fileWriter = new FileWriter(outputFile);
			
			Transcript transcript = client.transcripts().transcribe(audioFile);
			
			if (transcript.getStatus().toString().contentEquals("error")) {
				System.err.println(transcript.getError());
			} else {

				// Write transcript to output file

				fileWriter.write(String.valueOf(transcript.getText()));

				fileWriter.close();

				// Opens output file

				Desktop desktop = Desktop.getDesktop();

				desktop.open(outputFile);

			} 
			
			
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}	
		
	}

}
