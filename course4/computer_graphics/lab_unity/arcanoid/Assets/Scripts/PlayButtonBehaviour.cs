using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class PlayButtonBehaviour : MonoBehaviour {
    // Перерисовка кадра игры
    void Update() {
        if (Input.GetKeyDown(KeyCode.Return)) {
            loadGame();
        }
    }

    public void loadGame() {
        SceneManager.LoadScene("Level1");
    }
}
