package msk.android.academy.javatemplate.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import msk.android.academy.javatemplate.network.StepsItemDTO;
import msk.android.academy.javatemplate.room.StepsItemDB;

public class Mapper {

    public static List<StepsItemDB> DtoToDb(List<StepsItemDTO> listDto) {

        final List<StepsItemDB> list = new ArrayList<>();

        for (StepsItemDTO item_dto : listDto) {
            list.add(
                    new StepsItemDB(
                            item_dto.getDate(),
                            item_dto.getWalk(),
                            item_dto.getAerobic(),
                            item_dto.getRun()
                    )
            );
            Log.d("room", "items added to DB: " + item_dto.getDate() + " | "
                    + "[ " + item_dto.getWalk() + ", " + item_dto.getAerobic() + ", " + item_dto.getRun() + " ]");
        }
        return list;
    }

    public static List<StepsItem> DbToItem(List<StepsItemDB> listDb) {

        final List<StepsItem> list = new ArrayList<>();

        for (StepsItemDB item_db : listDb) {
            list.add(
                    new StepsItem(
                            item_db.getId(),
                            DateUtils.formatDateFromDb(item_db.getDate()),
                            item_db.getGoal(),
                            item_db.getWalk(),
                            item_db.getAerobic(),
                            item_db.getRun()
                    )
            );
//            Log.d("rv", "items added to Adapter: " + item_db.getDate() + " | "
//                    + "[ " + item_db.getWalk() + ", " + item_db.getAerobic() + ", " + item_db.getRun() + " ]");
        }
        return list;
    }

}
