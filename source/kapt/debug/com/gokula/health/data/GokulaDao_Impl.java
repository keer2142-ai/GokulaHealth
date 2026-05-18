package com.gokula.health.data;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Long;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class GokulaDao_Impl implements GokulaDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<CattleEntity> __insertAdapterOfCattleEntity;

  private final EntityInsertAdapter<MilkEntryEntity> __insertAdapterOfMilkEntryEntity;

  private final EntityInsertAdapter<VaccinationEntity> __insertAdapterOfVaccinationEntity;

  private final EntityInsertAdapter<HeatCycleEntity> __insertAdapterOfHeatCycleEntity;

  public GokulaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfCattleEntity = new EntityInsertAdapter<CattleEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `cattle` (`id`,`earTagId`,`name`,`breed`,`dateOfBirth`,`gender`,`ownerName`,`photoUri`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final CattleEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getEarTagId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getEarTagId());
        }
        if (entity.getName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getName());
        }
        if (entity.getBreed() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getBreed());
        }
        statement.bindLong(5, entity.getDateOfBirth());
        if (entity.getGender() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getGender());
        }
        if (entity.getOwnerName() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getOwnerName());
        }
        if (entity.getPhotoUri() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getPhotoUri());
        }
        statement.bindLong(9, entity.getCreatedAt());
      }
    };
    this.__insertAdapterOfMilkEntryEntity = new EntityInsertAdapter<MilkEntryEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `milk_entries` (`id`,`cattleId`,`entryDate`,`session`,`litres`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final MilkEntryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCattleId());
        statement.bindLong(3, entity.getEntryDate());
        if (entity.getSession() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getSession());
        }
        statement.bindDouble(5, entity.getLitres());
        statement.bindLong(6, entity.getCreatedAt());
      }
    };
    this.__insertAdapterOfVaccinationEntity = new EntityInsertAdapter<VaccinationEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `vaccinations` (`id`,`cattleId`,`vaccineName`,`administeredDate`,`nextDueDate`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final VaccinationEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCattleId());
        if (entity.getVaccineName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getVaccineName());
        }
        statement.bindLong(4, entity.getAdministeredDate());
        statement.bindLong(5, entity.getNextDueDate());
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getNotes());
        }
      }
    };
    this.__insertAdapterOfHeatCycleEntity = new EntityInsertAdapter<HeatCycleEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `heat_cycles` (`id`,`cattleId`,`observedDate`,`projectedNextDate`,`reminderDate`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final HeatCycleEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCattleId());
        statement.bindLong(3, entity.getObservedDate());
        statement.bindLong(4, entity.getProjectedNextDate());
        statement.bindLong(5, entity.getReminderDate());
      }
    };
  }

  @Override
  public Object insertCattle(final CattleEntity cattle,
      final Continuation<? super Long> $completion) {
    if (cattle == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfCattleEntity.insertAndReturnId(_connection, cattle);
    }, $completion);
  }

  @Override
  public Object insertMilkEntry(final MilkEntryEntity entry,
      final Continuation<? super Long> $completion) {
    if (entry == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfMilkEntryEntity.insertAndReturnId(_connection, entry);
    }, $completion);
  }

  @Override
  public Object insertVaccination(final VaccinationEntity vaccination,
      final Continuation<? super Long> $completion) {
    if (vaccination == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfVaccinationEntity.insertAndReturnId(_connection, vaccination);
    }, $completion);
  }

  @Override
  public Object insertHeatCycle(final HeatCycleEntity heatCycle,
      final Continuation<? super Long> $completion) {
    if (heatCycle == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfHeatCycleEntity.insertAndReturnId(_connection, heatCycle);
    }, $completion);
  }

  @Override
  public Object getCattle(final Continuation<? super List<CattleEntity>> $completion) {
    final String _sql = "SELECT * FROM cattle ORDER BY name";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEarTagId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earTagId");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfBreed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "breed");
        final int _columnIndexOfDateOfBirth = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateOfBirth");
        final int _columnIndexOfGender = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gender");
        final int _columnIndexOfOwnerName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ownerName");
        final int _columnIndexOfPhotoUri = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "photoUri");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final List<CattleEntity> _result = new ArrayList<CattleEntity>();
        while (_stmt.step()) {
          final CattleEntity _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpEarTagId;
          if (_stmt.isNull(_columnIndexOfEarTagId)) {
            _tmpEarTagId = null;
          } else {
            _tmpEarTagId = _stmt.getText(_columnIndexOfEarTagId);
          }
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpBreed;
          if (_stmt.isNull(_columnIndexOfBreed)) {
            _tmpBreed = null;
          } else {
            _tmpBreed = _stmt.getText(_columnIndexOfBreed);
          }
          final long _tmpDateOfBirth;
          _tmpDateOfBirth = _stmt.getLong(_columnIndexOfDateOfBirth);
          final String _tmpGender;
          if (_stmt.isNull(_columnIndexOfGender)) {
            _tmpGender = null;
          } else {
            _tmpGender = _stmt.getText(_columnIndexOfGender);
          }
          final String _tmpOwnerName;
          if (_stmt.isNull(_columnIndexOfOwnerName)) {
            _tmpOwnerName = null;
          } else {
            _tmpOwnerName = _stmt.getText(_columnIndexOfOwnerName);
          }
          final String _tmpPhotoUri;
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null;
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          _item = new CattleEntity(_tmpId,_tmpEarTagId,_tmpName,_tmpBreed,_tmpDateOfBirth,_tmpGender,_tmpOwnerName,_tmpPhotoUri,_tmpCreatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getCattleById(final long id, final Continuation<? super CattleEntity> $completion) {
    final String _sql = "SELECT * FROM cattle WHERE id = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEarTagId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earTagId");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfBreed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "breed");
        final int _columnIndexOfDateOfBirth = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateOfBirth");
        final int _columnIndexOfGender = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gender");
        final int _columnIndexOfOwnerName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ownerName");
        final int _columnIndexOfPhotoUri = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "photoUri");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final CattleEntity _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpEarTagId;
          if (_stmt.isNull(_columnIndexOfEarTagId)) {
            _tmpEarTagId = null;
          } else {
            _tmpEarTagId = _stmt.getText(_columnIndexOfEarTagId);
          }
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpBreed;
          if (_stmt.isNull(_columnIndexOfBreed)) {
            _tmpBreed = null;
          } else {
            _tmpBreed = _stmt.getText(_columnIndexOfBreed);
          }
          final long _tmpDateOfBirth;
          _tmpDateOfBirth = _stmt.getLong(_columnIndexOfDateOfBirth);
          final String _tmpGender;
          if (_stmt.isNull(_columnIndexOfGender)) {
            _tmpGender = null;
          } else {
            _tmpGender = _stmt.getText(_columnIndexOfGender);
          }
          final String _tmpOwnerName;
          if (_stmt.isNull(_columnIndexOfOwnerName)) {
            _tmpOwnerName = null;
          } else {
            _tmpOwnerName = _stmt.getText(_columnIndexOfOwnerName);
          }
          final String _tmpPhotoUri;
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null;
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          _result = new CattleEntity(_tmpId,_tmpEarTagId,_tmpName,_tmpBreed,_tmpDateOfBirth,_tmpGender,_tmpOwnerName,_tmpPhotoUri,_tmpCreatedAt);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getMilkEntriesSince(final long cattleId, final long from,
      final Continuation<? super List<MilkEntryEntity>> $completion) {
    final String _sql = "SELECT * FROM milk_entries WHERE cattleId = ? AND entryDate >= ? ORDER BY entryDate";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cattleId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, from);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfCattleId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "cattleId");
        final int _columnIndexOfEntryDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "entryDate");
        final int _columnIndexOfSession = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "session");
        final int _columnIndexOfLitres = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "litres");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final List<MilkEntryEntity> _result = new ArrayList<MilkEntryEntity>();
        while (_stmt.step()) {
          final MilkEntryEntity _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final long _tmpCattleId;
          _tmpCattleId = _stmt.getLong(_columnIndexOfCattleId);
          final long _tmpEntryDate;
          _tmpEntryDate = _stmt.getLong(_columnIndexOfEntryDate);
          final String _tmpSession;
          if (_stmt.isNull(_columnIndexOfSession)) {
            _tmpSession = null;
          } else {
            _tmpSession = _stmt.getText(_columnIndexOfSession);
          }
          final double _tmpLitres;
          _tmpLitres = _stmt.getDouble(_columnIndexOfLitres);
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          _item = new MilkEntryEntity(_tmpId,_tmpCattleId,_tmpEntryDate,_tmpSession,_tmpLitres,_tmpCreatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getAllMilkEntries(final long cattleId,
      final Continuation<? super List<MilkEntryEntity>> $completion) {
    final String _sql = "SELECT * FROM milk_entries WHERE cattleId = ? ORDER BY entryDate DESC";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cattleId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfCattleId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "cattleId");
        final int _columnIndexOfEntryDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "entryDate");
        final int _columnIndexOfSession = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "session");
        final int _columnIndexOfLitres = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "litres");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final List<MilkEntryEntity> _result = new ArrayList<MilkEntryEntity>();
        while (_stmt.step()) {
          final MilkEntryEntity _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final long _tmpCattleId;
          _tmpCattleId = _stmt.getLong(_columnIndexOfCattleId);
          final long _tmpEntryDate;
          _tmpEntryDate = _stmt.getLong(_columnIndexOfEntryDate);
          final String _tmpSession;
          if (_stmt.isNull(_columnIndexOfSession)) {
            _tmpSession = null;
          } else {
            _tmpSession = _stmt.getText(_columnIndexOfSession);
          }
          final double _tmpLitres;
          _tmpLitres = _stmt.getDouble(_columnIndexOfLitres);
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          _item = new MilkEntryEntity(_tmpId,_tmpCattleId,_tmpEntryDate,_tmpSession,_tmpLitres,_tmpCreatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getVaccinations(final long cattleId,
      final Continuation<? super List<VaccinationEntity>> $completion) {
    final String _sql = "SELECT * FROM vaccinations WHERE cattleId = ? ORDER BY nextDueDate";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cattleId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfCattleId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "cattleId");
        final int _columnIndexOfVaccineName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "vaccineName");
        final int _columnIndexOfAdministeredDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "administeredDate");
        final int _columnIndexOfNextDueDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextDueDate");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final List<VaccinationEntity> _result = new ArrayList<VaccinationEntity>();
        while (_stmt.step()) {
          final VaccinationEntity _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final long _tmpCattleId;
          _tmpCattleId = _stmt.getLong(_columnIndexOfCattleId);
          final String _tmpVaccineName;
          if (_stmt.isNull(_columnIndexOfVaccineName)) {
            _tmpVaccineName = null;
          } else {
            _tmpVaccineName = _stmt.getText(_columnIndexOfVaccineName);
          }
          final long _tmpAdministeredDate;
          _tmpAdministeredDate = _stmt.getLong(_columnIndexOfAdministeredDate);
          final long _tmpNextDueDate;
          _tmpNextDueDate = _stmt.getLong(_columnIndexOfNextDueDate);
          final String _tmpNotes;
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes);
          }
          _item = new VaccinationEntity(_tmpId,_tmpCattleId,_tmpVaccineName,_tmpAdministeredDate,_tmpNextDueDate,_tmpNotes);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object countPendingVaccinations(final long until,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM vaccinations WHERE nextDueDate <= ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, until);
        final Integer _result;
        if (_stmt.step()) {
          final Integer _tmp;
          if (_stmt.isNull(0)) {
            _tmp = null;
          } else {
            _tmp = (int) (_stmt.getLong(0));
          }
          _result = _tmp;
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getHeatCycles(final long cattleId,
      final Continuation<? super List<HeatCycleEntity>> $completion) {
    final String _sql = "SELECT * FROM heat_cycles WHERE cattleId = ? ORDER BY observedDate DESC";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cattleId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfCattleId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "cattleId");
        final int _columnIndexOfObservedDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "observedDate");
        final int _columnIndexOfProjectedNextDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "projectedNextDate");
        final int _columnIndexOfReminderDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderDate");
        final List<HeatCycleEntity> _result = new ArrayList<HeatCycleEntity>();
        while (_stmt.step()) {
          final HeatCycleEntity _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final long _tmpCattleId;
          _tmpCattleId = _stmt.getLong(_columnIndexOfCattleId);
          final long _tmpObservedDate;
          _tmpObservedDate = _stmt.getLong(_columnIndexOfObservedDate);
          final long _tmpProjectedNextDate;
          _tmpProjectedNextDate = _stmt.getLong(_columnIndexOfProjectedNextDate);
          final long _tmpReminderDate;
          _tmpReminderDate = _stmt.getLong(_columnIndexOfReminderDate);
          _item = new HeatCycleEntity(_tmpId,_tmpCattleId,_tmpObservedDate,_tmpProjectedNextDate,_tmpReminderDate);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object countNearHeat(final long from, final long until,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM heat_cycles WHERE projectedNextDate BETWEEN ? AND ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, from);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, until);
        final Integer _result;
        if (_stmt.step()) {
          final Integer _tmp;
          if (_stmt.isNull(0)) {
            _tmp = null;
          } else {
            _tmp = (int) (_stmt.getLong(0));
          }
          _result = _tmp;
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
