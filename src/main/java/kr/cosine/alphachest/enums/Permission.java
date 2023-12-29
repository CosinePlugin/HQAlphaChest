package kr.cosine.alphachest.enums;

public enum Permission {
    CHEST("hqalphachest.use.");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission(int index) {
        return permission + index;
    }
}
