package com.gokula.health.data;

import androidx.annotation.NonNull;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile GokulaDao _gokulaDao;

  @Override
  @NonNull
  protected RoomOpenDelegate createOpenDelegate() {
    final RoomOpenDelegate _openDelegate = new RoomOpenDelegate(1, "18d1f81db32ccc367f10ad274861d13c", "195ab9424ad23473f3cd3b214f5bd5f6") {
      @Override
      public void createAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `cattle` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `earTagId` TEXT NOT NULL, `name` TEXT NOT NULL, `breed` TEXT NOT NULL, `dateOfBirth` INTEGER NOT NULL, `gender` TEXT NOT NULL, `ownerName` TEXT NOT NULL, `photoUri` TEXT, `createdAt` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `milk_entries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cattleId` INTEGER NOT NULL, `entryDate` INTEGER NOT NULL, `session` TEXT NOT NULL, `litres` REAL NOT NULL, `createdAt` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `vaccinations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cattleId` INTEGER NOT NULL, `vaccineName` TEXT NOT NULL, `administeredDate` INTEGER NOT NULL, `nextDueDate` INTEGER NOT NULL, `notes` TEXT NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `heat_cycles` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cattleId` INTEGER NOT NULL, `observedDate` INTEGER NOT NULL, `projectedNextDate` INTEGER NOT NULL, `reminderDate` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        SQLite.execSQL(connection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '18d1f81db32ccc367f10ad274861d13c')");
      }

      @Override
      public void dropAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `cattle`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `milk_entries`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `vaccinations`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `heat_cycles`");
      }

      @Override
      public void onCreate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      public void onOpen(@NonNull final SQLiteConnection connection) {
        internalInitInvalidationTracker(connection);
      }

      @Override
      public void onPreMigrate(@NonNull final SQLiteConnection connection) {
        DBUtil.dropFtsSyncTriggers(connection);
      }

      @Override
      public void onPostMigrate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      @NonNull
      public RoomOpenDelegate.ValidationResult onValidateSchema(
          @NonNull final SQLiteConnection connection) {
        final Map<String, TableInfo.Column> _columnsCattle = new HashMap<String, TableInfo.Column>(9);
        _columnsCattle.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCattle.put("earTagId", new TableInfo.Column("earTagId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCattle.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCattle.put("breed", new TableInfo.Column("breed", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCattle.put("dateOfBirth", new TableInfo.Column("dateOfBirth", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCattle.put("gender", new TableInfo.Column("gender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCattle.put("ownerName", new TableInfo.Column("ownerName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCattle.put("photoUri", new TableInfo.Column("photoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCattle.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysCattle = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesCattle = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCattle = new TableInfo("cattle", _columnsCattle, _foreignKeysCattle, _indicesCattle);
        final TableInfo _existingCattle = TableInfo.read(connection, "cattle");
        if (!_infoCattle.equals(_existingCattle)) {
          return new RoomOpenDelegate.ValidationResult(false, "cattle(com.gokula.health.data.CattleEntity).\n"
                  + " Expected:\n" + _infoCattle + "\n"
                  + " Found:\n" + _existingCattle);
        }
        final Map<String, TableInfo.Column> _columnsMilkEntries = new HashMap<String, TableInfo.Column>(6);
        _columnsMilkEntries.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilkEntries.put("cattleId", new TableInfo.Column("cattleId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilkEntries.put("entryDate", new TableInfo.Column("entryDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilkEntries.put("session", new TableInfo.Column("session", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilkEntries.put("litres", new TableInfo.Column("litres", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilkEntries.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysMilkEntries = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesMilkEntries = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMilkEntries = new TableInfo("milk_entries", _columnsMilkEntries, _foreignKeysMilkEntries, _indicesMilkEntries);
        final TableInfo _existingMilkEntries = TableInfo.read(connection, "milk_entries");
        if (!_infoMilkEntries.equals(_existingMilkEntries)) {
          return new RoomOpenDelegate.ValidationResult(false, "milk_entries(com.gokula.health.data.MilkEntryEntity).\n"
                  + " Expected:\n" + _infoMilkEntries + "\n"
                  + " Found:\n" + _existingMilkEntries);
        }
        final Map<String, TableInfo.Column> _columnsVaccinations = new HashMap<String, TableInfo.Column>(6);
        _columnsVaccinations.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaccinations.put("cattleId", new TableInfo.Column("cattleId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaccinations.put("vaccineName", new TableInfo.Column("vaccineName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaccinations.put("administeredDate", new TableInfo.Column("administeredDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaccinations.put("nextDueDate", new TableInfo.Column("nextDueDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaccinations.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysVaccinations = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesVaccinations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVaccinations = new TableInfo("vaccinations", _columnsVaccinations, _foreignKeysVaccinations, _indicesVaccinations);
        final TableInfo _existingVaccinations = TableInfo.read(connection, "vaccinations");
        if (!_infoVaccinations.equals(_existingVaccinations)) {
          return new RoomOpenDelegate.ValidationResult(false, "vaccinations(com.gokula.health.data.VaccinationEntity).\n"
                  + " Expected:\n" + _infoVaccinations + "\n"
                  + " Found:\n" + _existingVaccinations);
        }
        final Map<String, TableInfo.Column> _columnsHeatCycles = new HashMap<String, TableInfo.Column>(5);
        _columnsHeatCycles.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHeatCycles.put("cattleId", new TableInfo.Column("cattleId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHeatCycles.put("observedDate", new TableInfo.Column("observedDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHeatCycles.put("projectedNextDate", new TableInfo.Column("projectedNextDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHeatCycles.put("reminderDate", new TableInfo.Column("reminderDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysHeatCycles = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesHeatCycles = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHeatCycles = new TableInfo("heat_cycles", _columnsHeatCycles, _foreignKeysHeatCycles, _indicesHeatCycles);
        final TableInfo _existingHeatCycles = TableInfo.read(connection, "heat_cycles");
        if (!_infoHeatCycles.equals(_existingHeatCycles)) {
          return new RoomOpenDelegate.ValidationResult(false, "heat_cycles(com.gokula.health.data.HeatCycleEntity).\n"
                  + " Expected:\n" + _infoHeatCycles + "\n"
                  + " Found:\n" + _existingHeatCycles);
        }
        return new RoomOpenDelegate.ValidationResult(true, null);
      }
    };
    return _openDelegate;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final Map<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final Map<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "cattle", "milk_entries", "vaccinations", "heat_cycles");
  }

  @Override
  public void clearAllTables() {
    super.performClear(false, "cattle", "milk_entries", "vaccinations", "heat_cycles");
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final Map<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(GokulaDao.class, GokulaDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final Set<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public GokulaDao gokulaDao() {
    if (_gokulaDao != null) {
      return _gokulaDao;
    } else {
      synchronized(this) {
        if(_gokulaDao == null) {
          _gokulaDao = new GokulaDao_Impl(this);
        }
        return _gokulaDao;
      }
    }
  }
}
