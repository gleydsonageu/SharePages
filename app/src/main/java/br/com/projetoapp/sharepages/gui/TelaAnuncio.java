package br.com.projetoapp.sharepages.gui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.negocio.FotoServices;
import br.com.projetoapp.sharepages.negocio.LivroServices;
import br.com.projetoapp.sharepages.negocio.UnidadeLivroService;

public class TelaAnuncio extends Activity {

    private TextView nomeLivro, campoAutorAnuncio, campoEditoraAnuncio, campoPagAnuncio, campoEdicaoAnuncio, campoIdiomaAnuncio,
            campoDispoAnuncio, campoTemaAnuncio, campoCidadeAnuncio, campoDescricaoAnuncio;
    private ImageView preVisuFoto;
    private Button botaoAvaliacaoLivro, botaoConversarDono;
    private View relativeAnuncio;

    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

    LivroServices livroServices = LivroServices.getInstancia();
    UnidadeLivroService unidadeLivroService = UnidadeLivroService.getInstancia();
    FotoServices fotoServices = FotoServices.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_anuncio);
        int idUnidadeLivro = getIntent().getIntExtra("UnidadeLivroEscolhido", -1);

        SessaoUsuario.getInstancia().setContext(this);
        UnidadeLivro unidadeLivro = unidadeLivroService.buscarLivroPorId(idUnidadeLivro);


        nomeLivro = (TextView) findViewById(R.id.nomeLivro);
        campoAutorAnuncio = (TextView) findViewById(R.id.campoAutorAnuncio);
        campoEditoraAnuncio = (TextView) findViewById(R.id.campoEditoraAnuncio);
        campoPagAnuncio = (TextView) findViewById(R.id.campoPagAnuncio);
        campoEdicaoAnuncio = (TextView) findViewById(R.id.campoEdicaoAnuncio);
        campoIdiomaAnuncio = (TextView) findViewById(R.id.campoIdiomaAnuncio);
        campoDispoAnuncio = (TextView) findViewById(R.id.campoDispoAnuncio);
        campoTemaAnuncio = (TextView) findViewById(R.id.campoTemaAnuncio);
        campoCidadeAnuncio = (TextView) findViewById(R.id.campoCidadeAnuncio);
        campoDescricaoAnuncio = (TextView) findViewById(R.id.campoDescricaoAnuncio);
        preVisuFoto = (ImageView) findViewById(R.id.preVisuFoto);
        botaoAvaliacaoLivro = (Button) findViewById(R.id.botaoAvaliacaoLivro);
        botaoConversarDono = (Button) findViewById(R.id.botaoConversarDono);
        relativeAnuncio = (View) findViewById(R.id.relativeLayoutAnuncio);


        nomeLivro.setText(unidadeLivro.getLivro().getNome());
        campoAutorAnuncio.setText(unidadeLivro.getLivro().getAutor());
        campoEdicaoAnuncio.setText(unidadeLivro.getEdicao());
        campoEditoraAnuncio.setText(unidadeLivro.getEditora());
        campoPagAnuncio.setText("" + unidadeLivro.getNumeroPaginas());
        campoDescricaoAnuncio.setText(unidadeLivro.getDescricao());
        campoIdiomaAnuncio.setText(unidadeLivro.getIdioma());
        campoTemaAnuncio.setText(unidadeLivro.getLivro().getTema().getNome());
        campoCidadeAnuncio.setText(unidadeLivro.getUsuario().getCidade().getNome());
        campoDispoAnuncio.setText(unidadeLivro.getDisponibilidade().getNome());

        final Uri visualizacao = Uri.fromFile(new File(unidadeLivro.getFotos().get(0).getCaminho()));
        preVisuFoto.setImageURI(visualizacao);

        final View preVisuFotoView = (View) findViewById(R.id.preVisuFoto);
        preVisuFotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomImageFromThumb(preVisuFotoView, visualizacao);
            }
        });
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);



    }

    private void zoomImageFromThumb(final View thumbView, Uri uriVisualizacao) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.expanded_image);
        expandedImageView.setImageURI(uriVisualizacao);


        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.activity_tela_anuncio)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        relativeAnuncio.setVisibility(View.INVISIBLE);
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                        relativeAnuncio.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                        relativeAnuncio.setVisibility(View.VISIBLE);

                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

}
