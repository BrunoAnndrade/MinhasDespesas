package com.example.despesasdescomplicadas.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DespesaDao {

    @Query("SELECT orcamento FROM Orcamento WHERE id = 1")
    fun getOrcamentoFlow(): Flow<String>


    @Query("SELECT * FROM Despesa ORDER BY title ASC")
    fun getAllDespesaByname(): Flow<List<Despesa>>

    @Query("SELECT * FROM Despesa ORDER BY category ASC")
    fun getAllDespesaByCategory(): Flow<List<Despesa>>

    @Query("SELECT * FROM Despesa WHERE id = :despesaId LIMIT 1")
    suspend fun getDespesaById(despesaId: Int): Despesa?


    @Query("SELECT * FROM Despesa ORDER BY valor ASC")
    fun getAllDespesaByValor(): Flow<List<Despesa>>

    @Upsert
    suspend fun upsertDespesa(despesa: Despesa)

    @Delete
    suspend fun deleteDespesa(despesa: Despesa)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrçamento(orçamento: Orcamento)
    @Update
    suspend fun updateOrçamento(orçamento:Orcamento)
}
