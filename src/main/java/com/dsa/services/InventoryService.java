package com.dsa.services;

import com.dsa.domain.InventoryData;
import com.dsa.domain.PurchaseStatus;
import com.dsa.domain.StockLabelStatus;
import com.dsa.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    private final DatabaseConnection dbConnection;
    private Connection connection;

    public InventoryService(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.connection = dbConnection.connect();
    }

    private void ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dbConnection.connect();
        }
    }

    public InventoryData getById(int id) {
        InventoryData inventoryData = null;
        String query = "SELECT * FROM inventory.inventory WHERE id = ?";
        try (PreparedStatement preparedStatement = prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    inventoryData = toInventoryData(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
        return inventoryData;
    }

    public List<StockLabelStatus> getAllStockLabelStatus() {
        List<StockLabelStatus> stockStatus = new ArrayList<>();
        String query = "SELECT * FROM inventory.stock_label_status";
        try (PreparedStatement preparedStatement = prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                StockLabelStatus stockLabelStatus = new StockLabelStatus();
                stockLabelStatus.setId(resultSet.getInt("id"));
                stockLabelStatus.setStockStatus(resultSet.getString("status"));
                stockStatus.add(stockLabelStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
        return stockStatus;
    }

    public List<PurchaseStatus> getAllPurchaseStatus() {
        List<PurchaseStatus> purchaseStatuses = new ArrayList<>();
        String query = "SELECT * FROM inventory.purchase_status";
        try (PreparedStatement preparedStatement = prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                PurchaseStatus purchaseStatus = new PurchaseStatus();
                purchaseStatus.setId(resultSet.getInt("id"));
                purchaseStatus.setPurchaseStatus(resultSet.getString("status"));
                purchaseStatuses.add(purchaseStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
        return purchaseStatuses;
    }

    public InventoryData updateInventoryData(InventoryData inventoryData) {
        String query = "UPDATE inventory.inventory SET date_entered = ?, stock_label_status = ?, brand = ?, engine_number = ?, purchase_status = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(inventoryData.getDate().getTime()));
            preparedStatement.setObject(2, inventoryData.getStockLabelStatus() != null ? inventoryData.getStockLabelStatus().getId() : null, Types.INTEGER);
            preparedStatement.setString(3, inventoryData.getBrand());
            preparedStatement.setString(4, inventoryData.getEngineNumber());
            preparedStatement.setObject(5, inventoryData.getPurchaseStatus() != null ? inventoryData.getPurchaseStatus().getId() : null, Types.INTEGER);
            preparedStatement.setInt(6, inventoryData.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
        return inventoryData;
    }

    public InventoryData saveInventoryData(InventoryData inventoryData) {
        String query = "INSERT INTO inventory.inventory (date_entered, stock_label_status, brand, engine_number, purchase_status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, new java.sql.Date(inventoryData.getDate().getTime()));
            preparedStatement.setObject(2, inventoryData.getStockLabelStatus() != null ? inventoryData.getStockLabelStatus().getId() : null, Types.INTEGER);
            preparedStatement.setString(3, inventoryData.getBrand());
            preparedStatement.setString(4, inventoryData.getEngineNumber());
            preparedStatement.setObject(5, inventoryData.getPurchaseStatus() != null ? inventoryData.getPurchaseStatus().getId() : null, Types.INTEGER);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        inventoryData.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Saving inventory data failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
        return inventoryData;
    }

    public boolean deleteInventory(int id) {
        boolean isDeleted = false;
        String query = "DELETE FROM inventory.inventory WHERE id = ?";
        try (PreparedStatement preparedStatement = prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            isDeleted = affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
        return isDeleted;
    }

    public StockLabelStatus getStockLabelStatusById(int id) {
        StockLabelStatus stockLabelStatus = null;
        String query = "SELECT * FROM inventory.stock_label_status WHERE id = ?";
        try (PreparedStatement preparedStatement = prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    stockLabelStatus = new StockLabelStatus();
                    stockLabelStatus.setId(resultSet.getInt("id"));
                    stockLabelStatus.setStockStatus(resultSet.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
        return stockLabelStatus;
    }

    public PurchaseStatus getPurchaseStatusById(int id) {
        PurchaseStatus purchaseStatus = null;
        String query = "SELECT * FROM inventory.purchase_status WHERE id = ?";
        try (PreparedStatement preparedStatement = prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    purchaseStatus = new PurchaseStatus();
                    purchaseStatus.setId(resultSet.getInt("id"));
                    purchaseStatus.setPurchaseStatus(resultSet.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
        return purchaseStatus;
    }

    public List<InventoryData> getAllInventory() {
        List<InventoryData> allInventory = new ArrayList<>();
        String query = "SELECT * FROM inventory.inventory";
        try (PreparedStatement preparedStatement = prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                InventoryData inventoryData = toInventoryData(resultSet);
                allInventory.add(inventoryData);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
        return allInventory;
    }

    private InventoryData toInventoryData(ResultSet resultSet) throws SQLException {
        InventoryData inventoryData = new InventoryData();
        inventoryData.setDateEntered(resultSet.getDate("date_entered"));

        int stockLabelStatusId = resultSet.getInt("stock_label_status");
        if (stockLabelStatusId > 0) {
            StockLabelStatus stockLabelStatus = getStockLabelStatusById(stockLabelStatusId);
            if (stockLabelStatus != null) {
                inventoryData.setStockLabelStatus(stockLabelStatus);
            }
        }

        inventoryData.setBrand(resultSet.getString("brand"));
        inventoryData.setEngineNumber(resultSet.getString("engine_number"));

        int purchaseStatusId = resultSet.getInt("purchase_status");
        if (purchaseStatusId > 0) {
            PurchaseStatus purchaseStatus = getPurchaseStatusById(purchaseStatusId);
            inventoryData.setPurchaseStatus(purchaseStatus);
        }
        return inventoryData;
    }

    private PreparedStatement prepareStatement(String query) throws SQLException {
        ensureConnection();
        return connection.prepareStatement(query);
    }

    private PreparedStatement prepareStatement(String query, int autoGeneratedKeys) throws SQLException {
        ensureConnection();
        return connection.prepareStatement(query, autoGeneratedKeys);
    }
}