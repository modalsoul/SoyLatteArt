package jp.modal.soul.soylatteart

/**
 * Created by imae on 2015/12/17.
 */
trait Monitor {
  def sampling(target: Target): SamplingData
}
