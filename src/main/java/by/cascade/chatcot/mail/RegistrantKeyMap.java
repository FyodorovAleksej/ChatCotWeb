package by.cascade.chatcot.mail;

import by.cascade.chatcot.storage.databaseprocessing.user.UserModel;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RegistrantKeyMap {
    private static final int MAX_COUNT = 20;

    private static RegistrantKeyMap instance;
    private static Lock instanceLock = new ReentrantLock();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);

    private HashMap<String, UserModel> waitRegisterMap;

    private RegistrantKeyMap() {
        this.waitRegisterMap = new HashMap<>();
    }

    public static RegistrantKeyMap getInstance() {
        if (!isCreated.get()) {
            instanceLock.lock();
            if (instance == null) {
                instance = new RegistrantKeyMap();
                isCreated.set(true);
            }
            instanceLock.unlock();
        }
        return instance;
    }

    public String addValue(UserModel entity) {
        String uuid;
        int count = 0;
        do {
            uuid = UUID.randomUUID().toString();
            count++;
        } while (waitRegisterMap.containsKey(uuid) && count < MAX_COUNT);

        if (count == MAX_COUNT) {
            throw new RuntimeException("can't generate uuid");
        }

        waitRegisterMap.put(uuid, entity);
        return uuid;
    }

    public UserModel continueRegister(String uuid) {
        Optional<UserModel> optional = Optional.ofNullable(waitRegisterMap.remove(uuid));
        return optional.orElse(null);
    }

    public boolean checkLogin(String login) {
        if (login == null) {
            return false;
        }
        Collection<UserModel> collection = waitRegisterMap.values();
        for (UserModel user : collection) {
            if (user.getName().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEmail(String email) {
        if (email == null) {
            return false;
        }
        Collection<UserModel> collection = waitRegisterMap.values();
        for (UserModel user : collection) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
