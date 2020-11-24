package com.beluxha.task3.model.record;

import com.beluxha.task3.model.creation.entity.Batch;
import com.beluxha.task3.model.record.exception.NoSuchCodeException;
import com.beluxha.task3.model.record.entity.Record;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class GameRecordsHolderImpl implements GameRecordsHolder {
    private static final String STRING_SEPARATOR_FOR_DIFFERENT_GAME_TYPES = "`Э`";
    private static final String SEPARATOR_FOR_RECORD_FIELDS = "`Ъ`";
    private static final String FILE_PATH = "task_3/records";

    private final Map<Integer, Record> currentGameRecords = new HashMap<>();

    public GameRecordsHolderImpl() {
        log.info("Game records holder is decoding RECORDS TABLE");
        try (FileInputStream fileInputStream = new FileInputStream(new File(FILE_PATH))) {
            byte[] codedRecords = fileInputStream.readAllBytes();
            String decodedLine = new String(Base64.getDecoder().decode(codedRecords), StandardCharsets.UTF_16);
            String[] lines = decodedLine.split(STRING_SEPARATOR_FOR_DIFFERENT_GAME_TYPES);
            if(Batch.values().length != lines.length){
                createDefaultRecords();
            }
            for(String line : lines){
                String[] stringForCurrentDifficult = line.split(SEPARATOR_FOR_RECORD_FIELDS);
                int gameCode = Integer.parseInt(stringForCurrentDifficult[0]);
                currentGameRecords.put(gameCode, new Record(gameCode, stringForCurrentDifficult[1], Long.parseLong(stringForCurrentDifficult[2])));
            }
        } catch (Exception e) {
            createDefaultRecords();
            encodeCurrentRecordTable();
            log.error("Error while decoding records table");
        }
    }

    @Override
    public long getCurrentBestGameResult(int gameCode) throws NoSuchCodeException {
        if (currentGameRecords.get(gameCode) == null) {
            throw new NoSuchCodeException(gameCode);
        }
        return currentGameRecords.get(gameCode).getResult();
    }

    @Override
    public String getCurrentBestGamePlayerName(int gameCode) throws NoSuchCodeException {
        if (currentGameRecords.get(gameCode) == null) {
            throw new NoSuchCodeException(gameCode);
        }
        return currentGameRecords.get(gameCode).getPlayerName();
    }

    @Override
    public void setNewRecord(int gameCode, String playerName, long time) throws NoSuchCodeException {
        log.info("Game records holder is encoding RECORDS TABLE");
        for(Batch batch : Batch.values()){
            if(batch.getCode() == gameCode){
                currentGameRecords.put(gameCode, new Record(gameCode, playerName, time));
                encodeCurrentRecordTable();
            }
        }
        throw new NoSuchCodeException(gameCode);
    }

    @Override
    public List<Record> getRecords() {
        List<Record> records = new ArrayList<>();
        currentGameRecords.forEach((k, v) -> records.add(v));
        return records;
    }

    private void encodeCurrentRecordTable() {
        StringBuilder stringBuilder = new StringBuilder();
        currentGameRecords.forEach((k, v) -> {
            stringBuilder.append(createLineForEncoding(v));
            stringBuilder.append(STRING_SEPARATOR_FOR_DIFFERENT_GAME_TYPES);
        });
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(FILE_PATH))) {
            fileOutputStream.write(Base64.getEncoder().encode(stringBuilder.toString().getBytes(StandardCharsets.UTF_16)));
        } catch (IOException e) {
            log.error("Failed to write new record to file.");
        }
    }

    private String createLineForEncoding(Record record) {
        return record.getGameCode()
                + SEPARATOR_FOR_RECORD_FIELDS
                + record.getPlayerName()
                + SEPARATOR_FOR_RECORD_FIELDS
                + record.getResult();
    }

    private void createDefaultRecords() {
        for(Batch batch : Batch.values()){
            currentGameRecords.put(batch.getCode(), new Record(batch.getCode()));
        }
    }
}
