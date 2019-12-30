using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public enum BonusType {
    Life,
    TripleBalls,
    Increase,
    Decrease
}

public class BonusBehaviour : MonoBehaviour {
    public BonusType type = BonusType.Life;
    	
    // Инициализация
    void Start() {
    }

    // Перерисовка кадра игры
    void Update() {
        verifyDestructionNecessity();
    }

    void OnTriggerEnter2D(Collider2D collision) {
        GameObject platform = collision.gameObject;

        if (platform.tag == "Platform") {
            applyBonus(platform);
            Destroy(this.gameObject);
        }
    }

    void verifyDestructionNecessity() {
        if (transform.position.y < -5.2) {
            Destroy(this.gameObject);
        }
    }

    // Выполняет действие бонуса, свойственное его типу
    void applyBonus(GameObject platform) {
        switch(type) {
            case BonusType.Life:
                platform.SendMessage("addLife");
                break;
            case BonusType.TripleBalls:
                GameObject ball = GameObject.FindGameObjectsWithTag("Ball")[0];
                ball.SendMessage("addBalls");
                break;
            case BonusType.Increase:
                platform.SendMessage("increaseSize");
                break;
            case BonusType.Decrease:
                platform.SendMessage("decreaseSize");
                break;
        }
    }
}
