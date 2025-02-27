package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class TrainService {
    private List<Train> trainList;
    private static final String TRAINS_PATH= "app/src/main/java/ticket/booking/localdb/trains.json";
    private ObjectMapper objectMapper = new ObjectMapper();//using to map json to java variables
    public TrainService () throws IOException{
        this.trainList=loadTrains();
    }

    public List<Train> getTrainList() {
        return trainList;
    }

    public void setTrainList(List<Train> trainList) {
        this.trainList = trainList;
    }

    private void saveTrainListToFile()  {
        try{
            File usersFile = new File(TRAINS_PATH);
            objectMapper.writeValue(usersFile, trainList);
            //deserializing object to again json to save in file
        }
        catch (IOException e){
            System.out.println("Unable to save train list to file");
        }

    }

    public List<Train> loadTrains() throws IOException {
        File trains = new File(TRAINS_PATH);
        //Using Jackson Library by downloading from build.gradle to parse userID->user_id conversions automatically
        trainList = objectMapper.readValue(trains, new TypeReference<List<Train>>(){});
        return trainList;
    }
    public List<Train> searchTrain(String source,String destination) {
        return trainList.stream().filter(train->validTrain(train, source,destination)).collect(Collectors.toList());
    }
    private boolean validTrain(Train train, String source, String destination) {
        List<String> stationOrder = train.getStations();
        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());
        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex<destinationIndex;
    }
    public void addTrain(Train newTrain){
        Optional<Train> existingTrain = trainList.stream().filter(train->train.getTrainId().equalsIgnoreCase(newTrain.getTrainId())).findFirst();
        if(existingTrain.isPresent()){
            updateTrain(newTrain);
        }
        else{
            trainList.add(newTrain);
            saveTrainListToFile();
        }
    }
    public void updateTrain(Train updatedTrain){
        OptionalInt index = IntStream.range(0, trainList.size())
                .filter(i -> trainList.get(i).getTrainId().equalsIgnoreCase(updatedTrain.getTrainId()))
                .findFirst();

        if (index.isPresent()) {
            // If found, replace the existing train with the updated one
            trainList.set(index.getAsInt(), updatedTrain);
            saveTrainListToFile();
        } else {
            // If not found, treat it as adding a new train
            addTrain(updatedTrain);
        }
    }

}
