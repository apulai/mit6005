package piano;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sound.midi.MidiUnavailableException;

import midi.Midi;
import music.NoteEvent;
import music.NoteEvent.Kind;
import music.Pitch;




public class PianoMachine {
	
	private Midi midi;
    
	// valid values -2, -1, 0, 1, 2
	private int current_octave=0;
	midi.Instrument current_instrument;
	int recordingstate=0;
	public List<NoteEvent> mytune;
	Pitch p1;
	
	/**
	 * constructor for PianoMachine.
	 * 
	 * initialize midi device and any other state that we're storing.
	 */
    public PianoMachine() {
    	try {
            midi = Midi.getInstance();
        } catch (MidiUnavailableException e1) {
            System.err.println("Could not initialize midi device");
            e1.printStackTrace();
            return;
        }
    	current_instrument=midi.DEFAULT_INSTRUMENT;
		mytune = new ArrayList<NoteEvent>();
    }
    
    
    //TODO write method spec
    // Play the sound when called.
    // Use the internal values of the class current_instrument and active current_octave to modify to sound
    // When recording mode is enabled add the note to the end of the list
    // @Param: rawPitch: sound to play
    // @Param: return: no value returned
    public void beginNote(Pitch rawPitch) {
    	p1=rawPitch.transpose(12*current_octave);
    	midi.beginNote(p1.toMidiFrequency(), current_instrument);
    	// Are we recoding
    	if( recordingstate == 1)
    	{
    		long when=System.currentTimeMillis();
    		System.out.println("when "+when);
    		NoteEvent n1=new NoteEvent(p1, when, current_instrument, Kind.start);
    		System.out.println("New note"+n1);
    		System.out.println(n1.toString());
    		mytune.add(n1);

    	}
    		
    		
    	//TODO implement for question 1

    }
    
    //TODO write method spec
    // Stop playing the sound when called.
    // Use the internal values of the class current_instrument and active current_octave to modify to sound
    // When recording mode is enabled add the note to the end of the list
    // @Param: rawPitch: sound to play
    // @Param: return: no value returned
  //TODO implement for question 1
    public void endNote(Pitch rawPitch) {
    	p1=rawPitch.transpose(12*current_octave);
    	midi.endNote(p1.toMidiFrequency(), current_instrument);
    	if( recordingstate == 1)
    	{
    		long when=System.currentTimeMillis();
    		NoteEvent n1=new NoteEvent(p1, when, current_instrument, Kind.stop);
    		System.out.println(n1.toString());
    		mytune.add(n1);
    	}
    	
    }
    
    //TODO write method spec
    //Change instrument, when no more instrument in the list start over 
    // with the default instrument
    // Print out the current instrument
    public void changeInstrument() {
       	//TODO: implement for question 2
    	current_instrument = current_instrument.next();
    	System.out.println(current_instrument.name());
    }
    
    //TODO write method spec
    // Use the class's internal state current_octave and increment the value by 1
    // maximum current octave vaule is 2
    public void shiftUp() {
    	if( current_octave < 2) 
    	{
    		current_octave++;
    	}
    	System.out.println("Current octave:" + current_octave);
    	//TODO: implement for question 3
    }
    
    //TODO write method spec
    // Use the class's internal state current_octave and decrement the value by 1
    // maximum current octave vaule is 2
    public void shiftDown() {
    	//TODO: implement for question 3
    	if( current_octave >= -1) 
    	{
    		current_octave--;
    	}
    	System.out.println("Current octave:" + current_octave);
    }
    
    //TODO write method spec
    public boolean toggleRecording() {
    	if(recordingstate==0) {
    		recordingstate=1;
    		System.out.println(mytune.size());
    		return true;
    	}
    	else
    	{
    	recordingstate=0;	
    	}
    	return false;
    	//TODO: implement for question 4
    }
    
    //TODO write method spec
    protected void playback() throws InterruptedException {
    

    	long t=0;
    	for(NoteEvent n: mytune)
    	{
    	if( t== 0 )
    		{
    			t=n.getTime();
    		}
    	else
    	{
    		Thread.sleep( n.getTime() - t);
    		t=n.getTime();
    		
    	}
    		
    	if(n.getKind() == Kind.start)
    		midi.beginNote(n.getPitch().toMidiFrequency(), n.getInstr());
    	if(n.getKind() == Kind.stop)
    		midi.endNote(n.getPitch().toMidiFrequency(), n.getInstr());
    	}
    		
        //TODO: implement for question 4
    }

}
