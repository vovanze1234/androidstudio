package com.example.employeedb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.employeedb.EmployeeDao
import com.example.employeedb.Employee

@Database(entities = [Employee::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao?
}