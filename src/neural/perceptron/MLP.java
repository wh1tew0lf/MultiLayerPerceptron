package neural.perceptron;

public class MLP {

    double[] enters;
    double[] hidden;
    double[] outers;
    double[][] wEH;     //weights from enters to hidden
    double[][] wHO;     //weights from hidden to outers

    double[][] patterns = {
            {0, 0}, {1, 0}, {0, 1}, {1, 1}
    };

    double[][] answers = {
            {0, 0}, {1, 1}, {0, 1}, {1, 1}
    };

    public MLP() {
        enters = new double[patterns[0].length];
        hidden = new double[15];
        outers = new double[answers[0].length];
        wEH = new double[enters.length][hidden.length];
        wHO = new double[hidden.length][outers.length];

        InitWeight();
    }

    public void InitWeight() {
        for (int i = 0; i < enters.length; i++) {
            for (int j = 0; j < wEH[i].length; j++) {
                wEH[i][j] = Math.random() * 0.2 + 0.1;
            }
        }

        for (int i = 0; i < wHO.length; i++) {
            for (int j = 0; j < wHO[i].length; j++) {
                wHO[i][j] = Math.random() * 0.2 + 0.1;
            }
        }
    }

    public void CountOuter() {
        for (int i = 0; i < hidden.length; i++) {
            hidden[i] = 0;
            for (int j = 0; j < enters.length; j++) {
                hidden[i] += enters[j] * wEH[j][i];
            }

            if (hidden[i] > 0.5)
                hidden[i] = 1;
            else hidden[i] = 0;
        }

        for(int i = 0; i < outers.length; ++i) {
            outers[i] = 0;
        }

        for (int i = 0; i < hidden.length; i++) {
            for(int j = 0; j < outers.length; ++j) {
                outers[j] += hidden[i] * wHO[i][j];
            }
        }

        for(int i = 0; i < outers.length; ++i) {
            outers[i] = outers[i] > 0.5 ? 1 : 0;
        }
    }

    public int Study() {
        double[] err = new double[hidden.length];
        double gError;

        int iterations = 0;
        do {
            iterations++;
            gError = 0;
            for (int p = 0; p < patterns.length; p++) {
                for (int i = 0; i < enters.length; i++) {
                    enters[i] = patterns[p][i];
                }

                CountOuter();

                for(int z = 0; z < outers.length; ++z) {
                    double lErr = answers[p][z] - outers[z];
                    gError += Math.abs(lErr);

                    for (int i = 0; i < hidden.length; i++) {
                        err[i] = lErr * wHO[i][z];
                    }

                    for (int i = 0; i < enters.length; i++) {
                        for (int j = 0; j < hidden.length; j++){
                            wEH[i][j] += 0.1 * err[j] * enters[i];
                        }
                    }

                    for (int i = 0; i < hidden.length; i++) {
                        wHO[i][z] += 0.1 * lErr * hidden[i];
                    }
                }
            }
        } while (gError != 0 && iterations < 1000);
        return iterations;
    }

    public void Test() {
        System.out.println(Study());

        for (int p = 0; p < patterns.length; p++) {
            for (int i = 0; i < enters.length; i++) {
                enters[i] = patterns[p][i];
            }

            CountOuter();

            for(int k = 0; k < outers.length; ++k) {
                System.out.print(outers[k]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

}

